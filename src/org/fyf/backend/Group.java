package org.fyf.backend;

import java.util.Vector;

public class Group {
	private int name;
	private Group L=null,R=null;
	private Vector<Integer> members;
	
	Group(int name)
	{
		this.members = new Vector<>();
		this.name = name;
	}
	
	public boolean exists(int index)
	{
		if(this.name != index)
		{
			if(this.L != null && this.name > index)
				return this.L.exists(index);
			else if(this.R != null && this.name < index)
				return this.R.exists(index);
			else return false;
		}else
			return true;
	}
	
	public boolean add(Group newGroup)
	{
		if(this.name > newGroup.name)
		{
			if(this.L == null)
			{
				this.L = newGroup;
				return true;
			}
			else
			{
				return this.L.add(newGroup);
			}
		}else if(this.name < newGroup.name)
		{
			if(this.R == null)
			{
				this.R = newGroup;
				return true;
			}
			else
			{
				return this.R.add(newGroup);
			}
		}else
			return false;
	}
	public String getMembers(int groupName)
	{
		if(this.name == groupName)
		{
			return getNameMembers();
		}else if(this.name < groupName && this.R != null)
		{
			return this.R.getMembers(groupName);
		}else if(this.name > groupName && this.L != null) {
			return this.L.getMembers(groupName);
		}else {
			return null;
		}
	}
	private String getNameMembers()
	{
		String msg = "";
		for(int i = 0;i<this.members.size();i++)
		{
			//System.out.println("Getting rekt by " + this.members.get(i));
			msg = msg.concat(",").concat(String.valueOf(this.members.get(i)));
		}
		return msg;
	}
	public void removeMember(int name)
	{
		int i = this.members.size();
		while(this.members.get(i) != name)
		{
			if(i <name)
			{
				if(i == (i + this.members.size())/2)
					return;
				i = (i + this.members.size())/2;
			}else
			{
				if(i == (this.members.size() - i)/2)
					return;
				i = (this.members.size() - i)/2;
			}
		}
		if(this.members.get(i) == name)
			this.members.remove(i);
	}
	public Group deleteGroup(int name)
	{
		if(this.name == name)
		{
			if(this.L != null)
			{
				deleteInfo();
				if(this.R != null)
				{
					this.L.add(this.R);
					return this.L;
				}
				else
				{
					return this.L;
				}
			}else
				return this.R;
		}else if(this.name < name && this.R != null){
			this.R = this.R.deleteGroup(name);
		}else if(this.name > name && this.L != null){
			this.L = this.L.deleteGroup(name);
		}
		return this;
	}
	private void deleteInfo()
	{
		this.members.removeAllElements();
	}
	public void print()
	{
		if(this.L != null)
			this.L.print();
		System.out.println("Group name:"+this.name);
		this.printMembers();
		if(this.R != null)
			this.R.print();
	}
	private void printMembers()
	{
		for(int i=0;i<this.members.size();i++)
			System.out.println("Member "+this.members.get(i));
	}
	public void addMember(int groupName, int userID)
	{
		if(this.name == groupName)
		{
			if(!this.members.contains(userID))
			{
				//System.out.println("Group: added"+userID+" to member list");
				this.members.addElement(userID);
			}
		}
		if(this.name > groupName && this.L != null)
			this.L.addMember(groupName, userID);
		else if(this.R != null && this.name < groupName)
			this.R.addMember(groupName, userID);
	}

}
