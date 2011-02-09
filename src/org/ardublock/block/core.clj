(ns org.ardublock.block.core (:gen-class))

(defn -main
  []
  (edu.mit.blocks.controller.WorkspaceController/main (into-array String ["/org/ardublock/block/lang_def.xml"])))

