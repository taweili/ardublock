#! /bin/bash

ARDUBLOCK_ZIP=ardublock.zip
ARDUBLOCK_DIR=ardublock.translations

if [ -f $ARDUBLOCK_ZIP ]; then 
	if [ -d $ARDUBLOCK_DIR ]; then
		rm -rf $ARDUBLOCK_DIR
	fi
	mkdir $ARDUBLOCK_DIR
	cd $ARDUBLOCK_DIR
	unzip -q ../$ARDUBLOCK_ZIP
	translated_files=`ls -l */* | grep -v "    93" | awk '{print $9}'`
	for file in $translated_files; do 
		prop_file=`basename $file .properties`_`dirname $file`.properties
		prop_file=`echo $prop_file | sed -e s/-/_/g`
		echo "process $file"
		native2ascii -encoding UTF8 $file > ../$prop_file
	done
	cd ..
	rm -rf $ARDUBLOCK_DIR
fi
