package com.swe573.socialhub;

import com.swe573.socialhub.domain.Notification;
import com.swe573.socialhub.domain.User;
import com.swe573.socialhub.repository.UserRepository;
import com.swe573.socialhub.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationTest {

@TestConfiguration
static class NotificationTestContextConfiguration
{
    @Bean
    public NotificationService service() {
        return new NotificationService();
    }
}
    @Autowired
    private NotificationService service;

    @MockBean
    private UserRepository userRepository;



    @Test
    public void contextLoads() throws Exception {
        assertNotNull(service);
    }





    @Test
    public void saveUser_shouldReturnNotificationList() {
        User user = new User(null, "test", "test@test.com", "test", null, 0);
        var notification = new Notification(null, "test message", "test", true, user);
        var notification2 = new Notification(null, "test message2", "", false, user);
        var list = new HashSet<Notification>();
        list.add(notification);
        list.add(notification2);
        user.setNotificationSet(list);
//        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        var dto = userService.mapUserToDTO(user);
//        assertTrue(dto.getNotifications().stream().count() == 2);
    }

//    @Test
//    public void whenValidUser_thenUserShouldBeSaved() {
//        User user = new User();
//        user.setUserName("test");
//        user.setBio("testbio");
//        user.setId(UUID.randomUUID());
//        user.setFollows(Collections.emptySet());
//        final Interest interest = new Interest();
//        interest.setName("Golf");
//        user.setInterests(Set.of(interest));
//        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//        NewUserDTO newUserParams = new NewUserDTO(user.getUserName(), user.getBio(), List.of(interest.getName()), "somelongpassword");
//        Mockito.when(followRepository.findByFollowee(Mockito.any(User.class))).thenReturn(Collections.emptyList());
//        UserDTO createdUser = userService.signUp(newUserParams);
//        Assertions.assertEquals(createdUser.getId(), user.getId().toString());
//    }
//
//    @Test
//    public void whenFollowed_followShouldBeSaved() {
//        final User currentUser = new User();
//        currentUser.setUserName("test_user");
//        currentUser.setId(UUID.randomUUID());
//
//
//        final User userToFollow = new User();
//        userToFollow.setUserName("followed_user");
//        userToFollow.setId(UUID.randomUUID());
//
//        final Follower followEntity = new Follower();
//        followEntity.setFollower(currentUser);
//        followEntity.setFollowee(userToFollow);
//
//        Mockito.when(userRepository.findUserByUserName(currentUser.getUserName())).thenReturn(Optional.of(currentUser));
//        Mockito.when(userRepository.findById(userToFollow.getId())).thenReturn(Optional.of(userToFollow));
//        Mockito.when(followRepository.save(Mockito.any(Follower.class))).thenReturn(followEntity);
//        final String followedUserId = userService.followUser(userToFollow.getId().toString(), currentUser::getUserName);
//        Assertions.assertEquals(userToFollow.getId().toString(), followedUserId);
//    }
//
//    @Test
//    public void whenUnfollowed_followShouldBeDeleted() {
//        final User currentUser = new User();
//        currentUser.setUserName("test_user");
//        currentUser.setId(UUID.randomUUID());
//
//        final User userToUnfollow = new User();
//        userToUnfollow.setUserName("unfollowed_user");
//        userToUnfollow.setId(UUID.randomUUID());
//
//        final Follower followToDelete = new Follower();
//        followToDelete.setFollowee(userToUnfollow);
//        followToDelete.setFollower(currentUser);
//
//        Mockito.when(userRepository.findUserByUserName(currentUser.getUserName())).thenReturn(Optional.of(currentUser));
//        Mockito.when(userRepository.findById(userToUnfollow.getId())).thenReturn(Optional.of(userToUnfollow));
//        Mockito.when(followRepository.findByFolloweeAndFollower(Mockito.any(User.class), Mockito.any(User.class))).thenReturn(Optional.of(followToDelete));
//        final String followedUserId = userService.unfollowUser(userToUnfollow.getId().toString(), currentUser::getUserName);
//        Mockito.verify(followRepository).delete(Mockito.any(Follower.class));
//        Assertions.assertEquals(userToUnfollow.getId().toString(), followedUserId);
//    }
//
//    @Test
//    public void whenQueriedByName_serviceShouldFind() {
//        final User currentUser = new User();
//        currentUser.setUserName("test_user");
//        currentUser.setId(UUID.randomUUID());
//        currentUser.setFollows(Collections.emptySet());
//
//        final User targetUser = new User();
//        targetUser.setUserName("target_user");
//        targetUser.setId(UUID.randomUUID());
//        targetUser.setFollows(Collections.emptySet());
//        targetUser.setInterests(Collections.emptySet());
//
//        Mockito.when(userRepository.findUserByUserName(currentUser.getUserName())).thenReturn(Optional.of(currentUser));
//        Mockito.when(userRepository.findUserByUserName(targetUser.getUserName())).thenReturn(Optional.of(targetUser));
//        Mockito.when(followRepository.findByFollowee(Mockito.any(User.class))).thenReturn(Collections.emptyList());
//
//        final UserDTO foundUser = userService.fetchUserByUserName(targetUser.getUserName(), currentUser::getUserName);
//        Assertions.assertEquals(foundUser.getId(), targetUser.getId().toString());
//    }
//
//    @Test
//    public void whenQueriedById_serviceShouldFind() {
//        final User currentUser = new User();
//        currentUser.setUserName("test_user");
//        currentUser.setId(UUID.randomUUID());
//        currentUser.setFollows(Collections.emptySet());
//
//        final User targetUser = new User();
//        targetUser.setUserName("target_user");
//        targetUser.setId(UUID.randomUUID());
//        targetUser.setFollows(Collections.emptySet());
//        targetUser.setInterests(Collections.emptySet());
//
//        Mockito.when(userRepository.findUserByUserName(currentUser.getUserName())).thenReturn(Optional.of(currentUser));
//        Mockito.when(userRepository.findById(targetUser.getId())).thenReturn(Optional.of(targetUser));
//        Mockito.when(followRepository.findByFollowee(Mockito.any(User.class))).thenReturn(Collections.emptyList());
//
//        final UserDTO foundUser = userService.fetchUser(targetUser.getId().toString(), currentUser::getUserName);
//
//        Assertions.assertEquals(foundUser.getUserName(), targetUser.getUserName());
//    }
}