# TODO: Clean up this mess of a Makefile. It's a horrid kludge at the moment.

ALL : doc/index.html JBarcodeBean.jar Demo.jar 

doc/index.html : src/jbarcodebean/JBarcodeBean.java
	javadoc -d doc -header "JBarcodeBean" -bottom "<center>JBarcodeBean - Copyright &copy; 2004, Dafydd Walters.</center>" -doctitle "JBarcodeBean 1.0.2 Javadocs" -windowtitle "JBarcodeBean 1.0.2 Javadocs" -group "JBarcodeBean Packages" com.dragontechnology.barcode -overview misc/overview.html -sourcepath src @misc/javadoc.list

JBarcodeBean.jar : classes/jbarcodebean/JBarcodeBean.class
	cp -f misc/jbarcodebean*.gif classes/jbarcodebean
	jar cfm JBarcodeBean.jar misc/JBarcodeBean.manifest -C classes jbarcodebean -C classes Acme

classes/jbarcodebean/JBarcodeBean.class : src/jbarcodebean/JBarcodeBean.java
	javac -d classes src/Acme/*.java src/Acme/jpm/Encoders/*.java src/jbarcodebean/*.java

classes/bardemo/Demo.class : src/bardemo/Demo.java JBarcodeBean.jar
	javac -classpath JBarcodeBean.jar -d classes src/bardemo/Demo.java src/bardemo/HelpViewer.java src/bardemo/JBeanPropertyEditor.java

Demo.jar : classes/bardemo/Demo.class doc/index.html
	cp -f misc/demoicon.gif classes/bardemo
	cp -f misc/property_icon.gif classes/bardemo
	cp -f misc/SimpleDemoScreenshot.gif classes/bardemo
	cp -f misc/uml_diag.gif classes/bardemo
	cp -f misc/JBarcodeBean.html classes/bardemo
	cp -f misc/Examples.html classes/bardemo
	cp -Rf doc classes/bardemo
	jar cfm Demo.jar misc/Demo.manifest -C classes bardemo doc

clean :
	rm -rf doc/*.html doc/*.css doc/package-list doc/resources doc/Acme doc/jbarcodebean
	rm -rf classes/Acme classes/jbarcodebean classes/bardemo
	rm -f JBarcodeBean.jar
	rm -f Demo.jar


