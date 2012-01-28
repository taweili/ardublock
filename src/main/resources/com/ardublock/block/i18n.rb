require 'nokogiri'
f = File.open "ardublock_def.xml"
doc = Nokogiri::XML(f)
bgs = doc.css "BlockGenus"
bgs.each {|bg| print "bg.#{bg['name']}=#{bg['initlabel']}\n" }