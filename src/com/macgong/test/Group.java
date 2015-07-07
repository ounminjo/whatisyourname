package com.macgong.test;

import java.util.ArrayList;
import java.util.Map;

import android.util.Log;

public class Group {
	private Group mParent;
	private ArrayList<Group> mChildren; // 트리구조로 사용하기 위해 사용하는 변수
										// 컴공/어드민/1기,2기,3기.. 같은 형식에서 사용
	private ArrayList<Map<String, String>> mData;
	private String mGroupName;
	private String mGroupKey;
	private int mDapth;
	public static ArrayList<Map<String, String>> mAdmin;
	private static Group mRootTreeOfGroup = null;

	public Group(String groupKey, String groupName, int dapth, Group parent) {
		mParent = parent;
		mData = null; // 자신이 최하위 레벨일때만 존재하는것 ?
		mChildren = null; // 자신의 하위 레벨의 그룹이 있을수도 있기때문
		mGroupKey = groupKey;
		mGroupName = groupName;
		mDapth = dapth;
	}

	public static void InitAdmin(ArrayList<Map<String, String>> input_admin) {
		mAdmin = input_admin;
	}

	public void addData(Map<String, String> input) {
		if (mData == null) // 리스트가 처음에 생성이 안되어있으면 새로 생성
			mData = new ArrayList<Map<String, String>>();
		mData.add(input);
	}

	public ArrayList<Map<String, String>> getData() {
		return mData;
	}

	public String getGroupName() {
		return mGroupName;
	}

	public String getGroupKey() {
		return mGroupKey;
	}

	public Group getParent() {
		return mParent;
	}

	public ArrayList<Group> getChildren() {
		return mChildren;
	}

	public void addChildren(Group input) {
		if (mChildren == null)
			mChildren = new ArrayList<Group>();

		for (int i = 0; i < mChildren.size(); i++) {
			if (input.getGroupKey().compareTo(mChildren.get(i).getGroupKey()) == 0) {
				// 연결된 자식들중에 같은 Key를 가진 그룹이 있다면 중단!
				Log.e("e", "addChildren에서오류, 동일한 Key값을 가진 child그룹이 존재!");
				return;
			} else if (input.getGroupKey().compareTo(mChildren.get(i).getGroupKey()) < 0) {
				mChildren.add(i, input);
				return;
			}

		}
		mChildren.add(input);
	}

	public static Group makeGroup(ArrayList<Map<String, String>> input) {
		int maxDepthSize = 0;
		for (int i = 0; i < input.size(); i++) {
			if (maxDepthSize < Integer.parseInt(input.get(i).get(StaticClass.GROUP_DEPTH))) { // 만들려는
																								// 그룹의
																								// 깊이가
																								// 한계깊이보다
																								// 클때
				maxDepthSize = Integer.parseInt(input.get(i).get(StaticClass.GROUP_DEPTH)); // 한계깊이
																							// 갱신
			}
		}

		ArrayList<Map<String, String>> groupList = new ArrayList<Map<String, String>>();

		// group_depth를 기준으로 오름차순으로 정렬한다. 정렬하는 이유는??
		for (int i = 0; i <= maxDepthSize; i++) {
			for (int j = 0; j < input.size(); j++) {
				if (i == Integer.parseInt(input.get(j).get(StaticClass.GROUP_DEPTH))) {
					groupList.add(input.get(j));
					input.remove(j);
					j--;
				}
			}
		}

		// Root 생성
		Group treeOfGroup = new Group(StaticClass.GROUP_TREE_ROOT, StaticClass.GROUP_TREE_ROOT, 0, null);

		for (int i = 0; i < groupList.size(); i++) {
			Group parent = getGroup(treeOfGroup, groupList.get(i).get(StaticClass.GROUP_PARENT_KEY));
			parent.addChildren(
					new Group(groupList.get(i).get(StaticClass.GROUP_KEY), groupList.get(i).get(StaticClass.GROUP_NAME),
							Integer.parseInt(groupList.get(i).get(StaticClass.GROUP_DEPTH)), parent));
		}
		return treeOfGroup;
	}

	public static ArrayList<Group> getParentGroups(int Depth) {
		ArrayList<Group> m_ParentList = Group.getRootTreeOfGroup().getChildren();
		if (Depth == 1) {
			return m_ParentList;
		}
		if (Depth != 0) {
			for (int i = 0; i < Depth; i++) {
				m_ParentList = m_ParentList.get(0).getChildren();
			}
		}
		return m_ParentList;
	}

	public static Group getGroup(Group treeOfGroup, String groupKey) {
		if (treeOfGroup == null)
			return null;

		ArrayList<Group> children = treeOfGroup.getChildren();

		String temp = treeOfGroup.getGroupKey().toString();
		if (temp.equals(groupKey.toString())) {
			return treeOfGroup;
		}
		if (groupKey.equals(treeOfGroup.getGroupKey())) {
			return treeOfGroup;
		}

		if (children == null)
			return null;

		// 트리 전체를 탐색 전위순회
		for (int i = 0; i < children.size(); i++) {
			if (groupKey.equals(children.get(i).getGroupKey()))
				return children.get(i);
			else {
				Group returnValue = getGroup(children.get(i), groupKey);
				if (returnValue != null) {
					return returnValue;
				}
			}
		}
		return null;
	}

	public static Group getGroup(String groupKey) {
		Group treeOfGroup = getRootTreeOfGroup();
		if (treeOfGroup == null)
			return null;

		ArrayList<Group> children = treeOfGroup.getChildren();

		if (groupKey.equals(treeOfGroup.getGroupKey()))
			return treeOfGroup;

		for (int i = 0; i < children.size(); i++) {
			if (groupKey.equals(children.get(i).getGroupKey()))
				return children.get(i);
			else {
				Group returnValue = getGroup(children.get(i), groupKey);
				if (returnValue != null)
					return returnValue;
			}
		}
		return null;
	}

	public static String getData(String incomingNumber) {
		String newIncoming = incomingNumber.substring(0, 3) + "-" + incomingNumber.substring(3, 7) + "-"
				+ incomingNumber.substring(7, 11);

		StringBuffer test = new StringBuffer("");
		Group temp;

		ArrayList<Map<String, String>> AllData = getDataAll(Group.getRootTreeOfGroup());

		for (int i = 0; i < AllData.size(); i++) {

			if (AllData.get(i).get(StaticClass.USER_PHONE).equals(newIncoming)) {

				temp = getGroup(AllData.get(i).get(StaticClass.USER_GROUP_KEY));
				test.insert(0, AllData.get(i).get(StaticClass.USER_NAME));
				while (temp != getRootTreeOfGroup()) {
					test.insert(0, temp.mGroupName + " ");
					temp = temp.getParent();
				}
			}
		}
		return test.toString();
	}

	public static ArrayList<Map<String, String>> getMemberByName(String name) {

		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> AllData = getDataAll(Group.getRootTreeOfGroup());

		for (int i = 0; i < AllData.size(); i++) {
			if (AllData.get(i).get(StaticClass.USER_NAME).equals(name)) {
				result.add(AllData.get(i));
			}
		}
		return result;
	}

	public static ArrayList<Map<String, String>> getMemberByNumber(String phone) {

		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		ArrayList<Map<String, String>> AllData = getDataAll(Group.getRootTreeOfGroup());

		for (int i = 0; i < AllData.size(); i++) {
			if (AllData.get(i).get(StaticClass.USER_PHONE).equals(phone)) {
				result.add(AllData.get(i));
			}
		}
		return result;
	}

	public static ArrayList<Map<String, String>> getDataAll(Group treeOfGroup) {
		if (treeOfGroup.getChildren() == null) {
			return treeOfGroup.getData();
		}

		ArrayList<Map<String, String>> returnValue = treeOfGroup.getData();
		if (returnValue == null) {
			returnValue = new ArrayList<Map<String, String>>();
		}
		for (int i = 0; i < treeOfGroup.getChildren().size(); i++) {
			ArrayList<Map<String, String>> childData = getDataAll(treeOfGroup.getChildren().get(i));
			if (childData != null) {
				for (int k = 0; k < childData.size(); k++) {
					returnValue.add(childData.get(k));
				}
			}
		}
		return returnValue;
	}

	public static Group getRootTreeOfGroup() {
		return mRootTreeOfGroup;
	}

	public int getDapth() {
		return mDapth;
	}

	public static void setRootTreeOfGroup(Group rootTreeOfGroup) {
		mRootTreeOfGroup = rootTreeOfGroup;
	}

	public static void setGroupData(Group treeOfGroup, Map<String, String> data) {
		Group group = getGroup(treeOfGroup, data.get(StaticClass.USER_GROUP_KEY));
		group.addData(data);
	}

	public static void setGroupData(Map<String, String> data) { // 개인정보 테이블에는
																// GROUP_KEY가
																// 존재하여 그걸로 삽입
		Group group = getGroup(data.get(StaticClass.USER_GROUP_KEY));
		group.addData(data);
	}
}
