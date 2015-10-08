package csctrails.configuration;

public class ConfigTest {
	
	public static void main(String[] args) {
		
		Configuration cfg = new Configuration();
		cfg.loadConfiguration("res/config");
		System.out.println(cfg.getProperty("FIXTURE_SHAPE_WIDTH@MODEL:KEY"));
	}

}
