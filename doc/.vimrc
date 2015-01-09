" Display trailing whitespace and tab (only for c, cpp and java file) 
autocmd FileType c,cpp,java  highlight WhitespaceEOL ctermbg=red guibg=red
autocmd FileType c,cpp,java  match WhitespaceEOL /\s\+$/
set enc=euc-kr
set title
set hlsearch

map <F3> <c-w><c-w>
map <F4> :Tlist<cr>

syntax on
filetype on


set nocompatible
set laststatus=2
set statusline=%h%F%m%r%=[%l:%c(%p%%)]
set background=dark
set backspace=eol,start,indent
set history=1000
set fileencodings=utf-8,euc-kr

"set cindent
"set autoindent
set shiftwidth=4
set sw=4
set ts=4
set sts=4
set nu
"set smartindent

set wrap
set history=999
"set ic
"set tags+=$HOME/1_f7/android/tags 
set tagbsearch

