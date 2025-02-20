package application;

import java.util.HashMap;
import java.util.Map;

public class Department {

	private String id;
	private String departmentName;
    private static Map<String, String> departmentNameToIdMap = new HashMap<>();
	private String managerName;
	private String companyId;

	// Constructor
	public Department(String id, String departmentName, String managerName, String companyId) {
		this.id = id;
		this.departmentName = departmentName;
        departmentNameToIdMap.put(departmentName, id);
		this.managerName = managerName;
		this.companyId = companyId;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	// Static method to get department ID by name
    public static String getDepartmentIdByName(String departmentName) {
        return departmentNameToIdMap.get(departmentName);
    }

    public static String[] getDepartmentNames() {
        return departmentNameToIdMap.keySet().toArray(new String[0]);
    }

	// toString method
	@Override
	public String toString() {
		return "Department{" + "id='" + id + '\'' + ", departmentName='" + departmentName + '\'' + ", managerName='"
				+ managerName + '\'' + ", companyId='" + companyId + '\'' + '}';
	}
}
