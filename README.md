# gnome-world
A simple experiment in animation using Swing and multi-threading that emulates a world in which gnomes travel a connected graph of villages.

#### Controls
*Left Click* - create a new village

*Right Click* - remove a village

*Middle Click on Village* - add a gnome to village under cursor

#### Notes
 - The starter class is `Drawer`
 - Comment and uncomment `Drawer.java:145` to enable or disable placing a gnome in a village upon village creation
 - **Red** roads are under construction. **Blue** roads are two way roads. All other roads are one way.
 - Removing a village while a gnome is there and has not yet decided where it will go next will cause that gnome to be permanently stuck!

![gnome-world](http://i.imgur.com/93UuVr6.png)
