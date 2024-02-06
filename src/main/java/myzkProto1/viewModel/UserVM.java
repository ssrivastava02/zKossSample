package myzkProto1.viewModel;
import myzkProto1.model.User; 

import myzkProto1.model.UserDao;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window; 

public class UserVM { 

	/*
	 * @Wire("#popupInputForm") private Window popupInputForm;
	 */
	private UserDao userDao;
	private boolean showModal = false;
	private User newUser;
	private User selectedUserToDelete;
	private List<User> userList;

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
		closeModal();

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

	@Command
	@NotifyChange("newUser")
	public void fetchUserData() {

		//popupInputForm.doModal();
		// Find the selected user
		User selectedUser = null;
		for (User user : userList) {
			if (user.isSelected()) {
				// Ensure only one user is selected
				if (selectedUser != null) {
					// If more than one user is selected, show a message and return
					Messagebox.show("Please select only one user to fetch data.");
					return;
				}
				selectedUser = user;
			}
		}

		// If no user is selected, show a message and return
		if (selectedUser == null) {
			Messagebox.show("Please select a user to fetch data.");
			return;
		}

		// Fetch user data
		User viewUser = userDao.getUserById(selectedUser.getId()); 
		if (viewUser != null) {
			// Populate text-boxes with user data
			newUser.setFirstName(viewUser.getFirstName());
			newUser.setLastName(viewUser.getLastName());
			newUser.setPhone(viewUser.getPhone());
			newUser.setDOB(viewUser.getDOB());
			newUser.setAddress(viewUser.getAddress());
			newUser.setEmail(viewUser.getEmail());
		} else {
			// Handle case where user data is not found
			Messagebox.show("User data not found for ID: " + selectedUser.getId());
		}
	}


	// Find the selected user by ID from the user list
	@Command
	public void selectUser(User user) {
		user.setSelected(!user.isSelected()); 
	}



	@NotifyChange("userList")
	public void loadUsers() {
		userList = userDao.getAllUsers(); // Fetch all users from the database

	}

	@Command
	@NotifyChange("showModal")
	public void openModal() {
		showModal = true;
	}

	@Command
	@NotifyChange("showModal")
	public void closeModal() {
		showModal = false;
	}

	public boolean isShowModal() {
		return showModal;
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

