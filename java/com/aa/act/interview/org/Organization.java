package com.aa.act.interview.org;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Organization {

	private Position root;
	
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		 
		  List<Position> collect = root.getDirectReports().stream().flatMap(p->Stream.concat(Stream.of(p), p.getDirectReports()
				  .stream().flatMap(x->Stream.concat(Stream.of(x), x.getDirectReports().stream())))).collect(Collectors.toList());
		  	collect.add(root);
		    Position position = collect.stream().filter(x->x.getTitle().equalsIgnoreCase(title)).findAny().orElse(null);
		//your code here		   
 
		  if(position !=null) {
			  try {
				  if(!position.isFilled()) {				  
					  Optional<Employee> emp = Optional.of(new Employee(position.hashCode(), person));
					  position.setEmployee(emp);
				  }
				 
				 return Optional.of(position);
			  }catch(Exception e) {
					return Optional.empty();
			  }
		  }
		 
		return Optional.empty();
	}
  
	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	 
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
