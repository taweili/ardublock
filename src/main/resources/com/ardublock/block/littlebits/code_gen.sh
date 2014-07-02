#! /bin/bash

echo '# littlebits ' > files/block-mapping.properties
echo '# littlebits ' > files/ardublock.properties
echo 'bd.littlebits=LittleBits' >> files/ardublock.properties

echo '<!-- littlebits -->' > files/ardublock.xml
echo '<!-- littlebits -->' > files/ardublock_drawer.xml
echo '			<BlockDrawer button-color="64 109 0" name="bd.littlebits">' >> files/ardublock_drawer.xml

#for fname in `ls */*1LR* | sed -e 's/_1LR.jpg$//'`; do 
#	bname=`echo $fname | tr 'A-Z' 'a-z'`
#	dname=`dirname $bname`
#	bname=`basename $bname`
#	echo $fname "----" $bname "---" $dname
#done


while read line; do
    arr=($line)
	fname=${arr[0]}
	ifname=$fname".jpg"
	cname=`basename $fname`
	bname="little_bits_"${arr[1]}
	lname="bg."$bname
	btype=${arr[2]}
	dname=$lname".description"
	
	echo ${bname}"="${btype} >> files/block-mapping.properties
	sed -e "s/BLOCK_NAME/$bname/g" -e "s/BLOCK_LABEL/$lname/g" -e "s/BLOCK_DESC/$cname/g" -e "s,BLOCK_IMAGE,$ifname,g" <  templates/ardublock_${btype}.xml.txt >> files/ardublock.xml
	echo "			    <BlockGenusMember>$bname</BlockGenusMember>" >> files/ardublock_drawer.xml
	echo "$lname=$cname" >> files/ardublock.properties
	echo "$dname=This is $cname" >> files/ardublock.properties
	echo $fname "---" $bname "---" $btype
done < block_desc.txt

echo '			</BlockDrawer>' >> files/ardublock_drawer.xml