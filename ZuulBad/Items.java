package ZuulBad;

import java.util.HashMap;

public abstract class Items {

		protected int weight;
		protected String name;
		protected String description;
		protected HashMap<String, String> descriptions;
		
		public String getDescription() {
			return description;
		}
		
		public String getName() {
			return name;
		}
		
		public int getWeight() {
			return weight;
		}

}

