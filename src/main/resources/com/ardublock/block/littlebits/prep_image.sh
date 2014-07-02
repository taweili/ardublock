#! /bin/sh

# echo "Cropping images"
# ls */*1LR* | sed -e 's/_1LR.jpg$//' | awk -F: '{printf "convert -crop 560x500+260+160 \"%s_1LR.jpg\" \"%s_crop.jpg\"\n", $1, $1}' | sh

echo "Resize images"
ls */*1LR* | sed -e 's/_1LR.jpg$//' | awk -F: '{printf "convert -resize 100x150 \"%s_1LR.jpg\" \"%s.jpg\"\n", $1, $1}' | sh

echo "Remove cropped images"
rm -rf */*_crop.jpg

# rm -f `ls */*1LR* | sed -e 's/_1LR.jpg$//'`

