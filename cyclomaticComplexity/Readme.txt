Short description:This is a eclipse plugin,made to calculate the cyclomatic complexity of C code,although it works
with java too.

Installation:Simply copy the .jar file from the "plugin" folder into your eclipse IDE's "plugins" folder,and then restart the IDE.
You should see the icon on the eclipse toolbar now.

Reinstallation: For installing a new bug-fix,firstly uninstall the program,following this link: https://wiki.eclipse.org/FAQ_How_do_I_remove_a_plug-in%3F ,then install it again.
Look:The plugin has a blue logo,consisting of 3 arrows,and it should be easy to locate on the toolbar.However,unless you are using
a text editor (that means,unless you are editing a .c/java/cpp file,the plugin will be inactive or "grayed out")

Use:Simply select the lines of code whose cyclomatic complexity you want to calculate,then press the button of the plugin.A small
dialog will appear,showing you the complexity and a color (green for <21,yellow for >21&<50,red for >50).The complexity is calculated simply
as 1+the number of control flow statements.
