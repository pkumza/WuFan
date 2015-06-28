WuFan - 悟饭
=====
#### A Scalable and Accurate Tool for Android App Clone Detection.

##Introduction

We create this simple tool **WuFan** for quick clone detection. All we need to do is choose two apps and press "Compare" Button. Just wait for several seconds and it will give you the similarity of the two apps. The Similarity ranges from 0 to 1.

##Current Version and Dev Environment
* __WuFan__ Current Version : 1.0.2
* JDK Version : Java 1.8.0_25
* IDE : Intellij Idea 14
* APKTOOL Version : 2.0.0

##Update History
### 1.0.2
Better Architecture for building artifacts.
### 1.0.1
Add comments to the code.
### 1.0.0
First runnable version.

##Quick Check
* Is Java 1.7 or 1.8 installed?
* Does executing java -version on command line / command prompt return 1.7 or 1.8？
* Is there a file named keytool (or keytool.exe in Windows) in directory **%jdk_home%**/bin/ ?
<br>
PS: **%jdk_home%** is set in **config.properties**, which is a config file under the root directory of this project.
* If you are using Unix/Linux, please make sure that files such as **apktool.jar**, **apktool** are really executable. If not, use *chmod* to change their permission.

## Usage
* __Mac OS X__:
 1. Download __WuFan__.
 2. Edit config.properties. Change __os__ as __mac__ and config your __jdk_home__.
<br>
For example : jdk_home=/Library/Java/Home
 3. Run the _main_ function of class __MainFrame__.
* __Windows__:
 1. Download __WuFan__.
 2. Edit config.properties. Change __os__ as __windows__ and config your __jdk_home__.
<br>
For example : jdk\_home=C:\\\\Program Files\\\\Java\\\\jdk1.8.0_25
 3. Run the _main_ function of class __MainFrame__.
* __Linux__:
 1. Download __WuFan__.
 2. Edit config.properties. Change __os__ as __linux__ and config your __jdk_home__.
<br>
For example : jdk\_home=/usr/java/jdk1.8.0_25 
 3. Run the _main_ function of class __MainFrame__.




##Sample
![sample](https://raw.githubusercontent.com/pkumza/WuFan/master/Sample.png)

