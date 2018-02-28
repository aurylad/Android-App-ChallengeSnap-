<?php

$con = mysqli_connect("localhost", "id3151107_challengesnap", "dviratis00", "id3151107_data");

$username = $_POST["username"];
$password = $_POST["password"];

$statement = mysqli_prepare($con, "SELECT * FROM user WHERE username = ? AND password = ?");
mysqli_stmt_bind_param($statement, "ss", $username, $password);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $user_id, $name, $username, $age, $password, $score, $challengeTitle);

$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($statement)){
	    $response["success"] = true;  
        $response["name"] = $name;
        $response["username"] = $username;
        $response["age"] = $age;
        $response["password"] = $password;
        $response["score"] = $score;
        $response["challengeTitle"] =$challengeTitle;
        $response["password"] =$password;



}
 echo json_encode($response);

?>