package application;

import java.util.HashMap;
import java.util.Map;

public class Company {

	private String companyId;
	private String companyName;
    private static Map<String, String> companyNameToIdMap = new HashMap<>();


	// Constructor
	public Company(String companyId, String companyName) {
		this.companyId = companyId;
		this.companyName = companyName;
		companyNameToIdMap.put(companyName, companyId);
	}

	// Getters and Setters
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
    public static String getCompanyNamebyID(String companyName) {
        return companyNameToIdMap.get(companyName);
    }

    public static String[] getCompanyNames() {
        return companyNameToIdMap.keySet().toArray(new String[0]);
    }

	// toString method
	@Override
	public String toString() {
		return "Company{" + "companyId='" + companyId + '\'' + ", companyName='" + companyName + '\'' + '}';
	}
}
