require 'nokogiri'
f = File.open "ardublock_def.xml"
doc = Nokogiri::XML(f)
bgs = doc.css "BlockGenus"
bgs.each do |bg|
  if bg['initlabel'] != "bg.#{bg['name']}"
    # print "bg.#{bg['name']}=#{bg['initlabel']}\n"
    bg['initlabel'] = "bg.#{bg['name']}"
  end
end
puts doc.to_xml(:indent => 2)
