(defproject org.ardublock/block "0.0.1-SNAPSHOT"
  :description "A Block Programming Language for Arduino"
  :url "http://github.com/taweili/ardublock"
  :license {:name "The GNU General Public License V3.0"
            :url "http://www.gnu.org/licenses/gpl.html"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.2.0"]
		 [org.clojure/clojure-contrib "1.2.0"]
		 [edu.mit/openblocks "1.0-SNAPSHOT"]]
  :main org.ardublock.block.core)
