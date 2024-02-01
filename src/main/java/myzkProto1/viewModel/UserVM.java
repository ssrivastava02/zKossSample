package myzkProto1.viewModel;
import myzkProto1.model.User; 

import myzkProto1.model.UserDao;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window; 

public class UserVM { 


	private UserDao userDao;

	private User newUser;
	private User selectedUserToDelete;
	private List<User> userList;

	@Wire("#popupInputForm")
	private Window popupInputForm;


	@Init
	public void init() {
		newUser = new User();
		userDao = new UserDao();
		loadUsers();
	}

	@Command
	@NotifyChange("userList")
	public void createUser() {
		userDao.createUser(newUser);
		// Add any necessary post-creation logic or UI updates
		loadUsers();
		newUser = new User(); // Clear the newUser object for next creation
		closePopup();


	}

	@Command
	@NotifyChange("userList")
	public void deleteUser() {
		List<User> selectedUsers = userList.stream().filter(User::isSelected).collect(Collectors.toList());
		for (User user : selectedUsers) {
			userDao.deleteUser(user.getId());
		}
		loadUsers();
	}

	// Find the selected user by ID from the user list
	@Command
	public void selectUser(User user) {
		user.setSelected(!user.isSelected()); 
	}

	public void closePopup() 
	{ 
		if (popupInputForm != null) 
		{
			popupInputForm.setVisible(false);
			Messagebox.show("User Added Successfully!","Information", Messagebox.OK, Messagebox.EXCLAMATION); 
		} 

	}


	@NotifyChange("userList")
	public void loadUsers() {
		userList = userDao.getAllUsers(); // Fetch all users from the database
	}

	public List<User> getUserList() {
		return userList;
	}

	// Getters and setters for properties
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public User getSelectedUserToDelete() {
		return selectedUserToDelete;
	}

	public void setSelectedUserToDelete(User selectedUserToDelete) {
		this.selectedUserToDelete = selectedUserToDelete;
	}
}

