# news_room
Lab5 Web Programming

Here is the home page, with all of its elements (for all cases).

![image](https://user-images.githubusercontent.com/56108881/167094258-930d55ab-8318-47a6-bbbf-1aeb9e0a64f4.png)

The post with dark background is a featured post. The two news pices (World and Nature category) are private news, only subscribers would see such blocks in their pages. The rest of this home page contains public posts. If the current user is an editor, they will see the edit button above each post.

The categories listed at the top lead to protected news - in order to access them, the user needs to ask for the password, which is sent through email. The admin can get the email either from the authenticated user's details or by asking for it from anonymous users.

This is the registration page:

![image](https://user-images.githubusercontent.com/56108881/167095354-fc7b3059-5990-478f-a406-8e3584ecfb4c.png)

And this is the login page:

![image](https://user-images.githubusercontent.com/56108881/167095479-3a00e009-96e8-404b-8032-1a96cb85f917.png)

All fields have constraints that ensure valid/secure usernames and passwords.

I added an example of a world news page (that still have security checks for user roles).

![image](https://user-images.githubusercontent.com/56108881/167095821-a2adb9dd-1b1b-45e5-b21b-b8a03ef5ce02.png)


Next, there are a few pages only administrators know about. They enable admins to see and edit users details, in order to assign or deassign roles, for example.

![image](https://user-images.githubusercontent.com/56108881/167094803-dbf47fd3-cc1a-4bc2-adc1-b8e8b0844d06.png)

![image](https://user-images.githubusercontent.com/56108881/167094892-c8a5f287-a435-4f2e-98b2-31c059f01106.png)

Lastly, here are som examples of security checks on the frontend side:

![image](https://user-images.githubusercontent.com/56108881/167096084-b6f63bf1-60be-424b-80c9-0940a4664b41.png)

![image](https://user-images.githubusercontent.com/56108881/167096166-744a0d14-9b59-4117-97dc-1bb21e3ad979.png)

![image](https://user-images.githubusercontent.com/56108881/167096275-cf0f5625-70eb-4d9c-89e4-5173e7caeafa.png)


