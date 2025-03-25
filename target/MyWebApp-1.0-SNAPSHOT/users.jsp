<html>
<body>
    <h1>Users List</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <!-- Display user list here -->
    </table>
    <h2>Add New User</h2>
    <form action="users" method="POST">
        Name: <input type="text" name="name"><br>
        Email: <input type="email" name="email"><br>
        <input type="submit" value="Add User">
    </form>
</body>
</html>
