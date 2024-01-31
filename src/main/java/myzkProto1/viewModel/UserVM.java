package myzkProto1.viewModel;
import myzkProto1.model.User; 

import myzkProto1.model.UserDao;

import java.util.List;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window; 

public class UserVM { 
	
	 	//@WireVariable
	 	private UserDao userDao;
	    
	    private User newUser;
	    private User selectedUserToDelete;
	    private List<User> userList;
	    
	    @Wire 
	    private Window popupInputForm;


	    @Init
	    public void init() {
	        newUser = new User();
	        userDao = new UserDao();
	        loadUsers();
	    }
	    
	    @Command
	    public void createUser() {
	        userDao.createUser(newUser);
	        // Add any necessary post-creation logic or UI updates
	        newUser = new User(); // Clear the newUser object for next creation
	        closePopup();
	        loadUsers();
	    }

	    @Command
	    public void deleteUser() {
	        if (selectedUserToDelete != null) {
	            userDao.deleteUser(selectedUserToDelete.getId());
	            // Add any necessary post-deletion logic or UI updates
	            loadUsers();
	        }
	    }
	    
	    public void closePopup() {
	        if (popupInputForm != null) {
	            popupInputForm.setVisible(false);
	          Messagebox.show("User Added Successfully!", "Information", Messagebox.OK, Messagebox.EXCLAMATION);
	        }
	    }
	    
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
  
  