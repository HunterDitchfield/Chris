Chris, since you are new you might not know where to look to find the source code and other important files that you need to write to. 
Some of the most important files are found at (YOUR APPLICATION NAME)/app/src/main, which would be Chris/app/src/main.
The three files and folders are 'java', 'res', and 'AndroidManifest.xml'.

'java' contains the package with all you java files. This is where 99% of coding goes on. Our java files are 'Main.java' and 'Game.java'.

'res' stands for resources, it contains our android layouts, strings, and images. The only one you really need to use is images. Its how 
I load custom icons and bitmaps for my games. Other things are useful too but you are less likely to use them.

'AndroidManifest.xml' is very important. It contains various things, one example is permissions. This sample project contains one permission,
<uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
That permission allows us to write code in our java files to add an icon shortcut to the homescreen of the user's device.
Under the acticty heading in the 'AndroidManifest.xml' you will see the tag android:screenOrientation="sensorLandscape"> which sets the
phone's orientation to landscape. 



I hope this helps you get an idea of where you need to go when you want to do something. If you need anything else let me know!
Happy Coding!
