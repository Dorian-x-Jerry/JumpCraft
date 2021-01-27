Dorian jan 5 1:25pm. created file

Jerry Jan 5 5:19pm. combined gravity and collision code to make rough demo of game jumping mechanics

Dorian jan 5 11:18 pm. tweaked gravity—collision with the top of walls will reduce yVelocity, instead of allowing the slime to essentially float. Found a bug: person will fall out of platforms—intended??

Dorian jan 6 12:02 am. fixed bug. collision physics should be all good now.

Dorian jan 7 12:04 am. Added winner() function and changed jump key to space. When you goto the very right of the screen. press E and it will check if you win. If not, it will print nothing. If you win, it will print ("you win")

Doiran jan 7 12:34 am. Added a background. Download the uploaded image and put it outside the src folder. Run the code and it should work.

Jerry Jan 7 6:25am. Created 3 separate levels under initialize method, added int level variable to track level number, set run method to stop when level reaches 4. To do: reset player location to bottom left at new level, create methods for title screen, win screen after winning level 3

Dorian Jan 7 1:28 pm. level changes & reseting position

Dorian Jan 10 12:11am. fixed collision, make the levels runable. For some reason, it needs to continously print "" to run. will fix later.

Jerry Jan 10 10:39am. made stylistic changes to keep consistency, moved mouse methods to top

Jerry Jan 10 11:09am. Added makeshift playericon attached to player rectangle

Jerry Jan 11 4:56pm. Made level 1, added door pic, changed wincheck to top right at the door

Dorian Jan 11 9:34pm. Made sliding stuff work, title screen, buttons.

Jerry Jan 12 3:05pm. created level 2, added stuff to lvl1 so you can't get stuck, fixed door win condition

Jerry Jan 13 2:02pm. started level 3, found collision bug when falling for long distances (go through platforms, register as bottom but not top collision)

Jerry Jan 15 2:45pm. fixed falling through platforms at high speeds bug by capping max y velocity at -29

Jerry Jan 16 12:15pm. finished creating level 3

Jerry Jan 16 10:23pm. fixed minor platform bug, renamed window to "Jumpcraft"

Dorian Jan 19 3:00pm. ramp physics + ramp

Jerry Jan 20 3:13pm. finished graphics for all levels

Jerry Jan 21 3:23pm. made skin selection screen, wacky bug where clicking on part of screen overlapped by level 0 and 1 mouse conditions makes screen switch rapidly between main and skin selection screen gave me seizure

Dorian Jan 25 2:00pm. Fixed skin selection bug

Jerry Jan 25 2:37pm. Added crown graphic and controls to main menu

Jerry Jan 26 8:53pm. Wrote internal, method, intro comments, polished stuff up

Jerry Jan 27 11:39am. Added our own skins' heads as options in skin selection menu

Dorian Jan 27 1:20pm. Added yellow outline when hovering over skins
