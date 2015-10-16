folder-diff
===========
Project "folder-diff" is my test task for JetBrains company.

It is an application that allows to browse differences of two directories or two files. "folder-diff" marks inserted, deleted and changed text.
The app has syntax highlighting for Java programming language. Also it's easy to add highlighting of any other language as a plugin.


Screenshots
===========
Differences between two directories displayed as follows:
![](https://raw.githubusercontent.com/avokin/folder-diff/master/sshots/folder.png)
File or folder that exists in the left window but missed in the right has gray background (it was deleted).
File or folder that exists in the right window but missed in the left has green background (it was created).
File that has differences between the left and the right version has blue background (it was changed).

Differences in files represented in the same way. Added line has green background, deleted - gray and changed - blue:
![](https://raw.githubusercontent.com/avokin/folder-diff/master/sshots/file.png)

Syntax highlighting implemented as follows:
![](https://raw.githubusercontent.com/avokin/folder-diff/master/sshots/highlighting.png)
