package myzkProto1.model;
import java.util.Comparator;
public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User user1, User user2) {
        // Compare first by firstName
        int firstNameComparison = user1.getFirstName().compareTo(user2.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        
        // If first names are equal, compare by userID
        return Integer.compare(user1.getId().intValue(), user2.getId().intValue());
    }
}
