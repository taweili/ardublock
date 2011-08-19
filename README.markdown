ArduBlock
======

ArduBlock is a Block Programming Language for Arduino.

Installation
----
The project is managed by Maven.

	$ mvn clean package

Usage
----

	$ mvn exec:java -Dexec.mainClass="com.ardublock.Main"

Development
----
Change the /src/main/resources/com/ardublock/block/ardublock_def.xml to add new blocks to ArduBlock

	$ mvn compile exec:java -Dexec.mainClass="com.ardublock.Main"

The Visual Block environment should show up. Happy Hacking! ;) 

License
----

Copyright (C) 2011 David Li and He Qicheng

This file is part of ArduBlock.

ArduBlock is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ArduBlock is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with ArduBlock.  If not, see <http://www.gnu.org/licenses/>.
