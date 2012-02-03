require 'nokogiri'
f = File.open "ardublock.xml"
doc = Nokogiri::XML(f)
bgs = doc.css "BlockGenus"
bgs.each do |bg|
  bg_name = "bg.#{bg['name']}"
  puts "#{bg_name}.description.text=#{bg.css("text").text.strip}"
  if bg['initlabel'] != bg_name
    # print "bg.#{bg['name']}=#{bg['initlabel']}\n"
    bg['initlabel'] = "bg.#{bg['name']}"
  end
end
# puts doc.to_xml(:indent => 2)
