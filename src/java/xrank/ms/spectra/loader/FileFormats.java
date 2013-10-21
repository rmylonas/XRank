package xrank.ms.spectra.loader;

public enum FileFormats {

	SDF ("sdf", "sdf", "all", true),
	MAURERSDF ("Maurer sdf", "sdf", "lib", true),
	BRUKERLIB ("Bruker library", "library", "lib", false),
	APPLIEDMDB ("Applied Biossystems mdb", "mdb", "all", true),
	APPLIEDCLIQUID ("Cliquid clq", "clq", "lib", true),
	BRUKERXML ("Bruker xml", "xml", "exp", false),
	MGF ("mgf", "mgf", "exp", false), 
	MZXML ("MzXML", "mzXML", "all", false),
	JPL ("Serialized JPL", "jpl", "exp", false);
	
	private final String name; 
	private final String extension;
	private final String type;
	private final Boolean sequential;
	
	FileFormats(String name, String extension, String type, Boolean sequential){
		this.name = name;
		this.extension = extension;
		this.type = type;
		this.sequential = sequential;
	}
	
	public Boolean getSequential(){
		return this.sequential;
	}
	
	public String getExtension(){
		return this.extension;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getType(){
		return this.type;
	}

}
