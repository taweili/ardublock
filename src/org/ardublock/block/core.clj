(ns org.ardublock.block.core (:gen-class))

(import '(javax.swing JFrame)
	'(javax.xml.parsers DocumentBuilderFactory)
	'(edu.mit.blocks.codeblocks BlockConnectorShape)
	'(edu.mit.blocks.codeblocks BlockGenus)
	'(edu.mit.blocks.codeblocks BlockLinkChecker)
	'(edu.mit.blocks.codeblocks CommandRule)
	'(edu.mit.blocks.codeblocks SocketRule)
	'(edu.mit.blocks.workspace Workspace))

(defn -main
  []
  (let [frame (JFrame. "ArduBlock")
	workspace (Workspace/getInstance)
	lang_def (. (. (. (DocumentBuilderFactory/newInstance) newDocumentBuilder) parse (str (. (.getClass "") getResource "/org/ardublock/block/lang_def.xml"))) getDocumentElement)]
    (. frame setSize (java.awt.Dimension. 1280 1024))
    (BlockConnectorShape/loadBlockConnectorShapes lang_def)
    (BlockGenus/loadBlockGenera lang_def)
    (BlockLinkChecker/addRule (CommandRule.))
    (BlockLinkChecker/addRule (SocketRule.))
    (. workspace loadWorkspaceFrom nil lang_def)
    (. frame add workspace)
    (. frame show)))
