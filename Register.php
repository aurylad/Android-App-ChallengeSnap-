<?php
    $con = mysqli_connect("localhost", "id3151107_challengesnap", "dviratis00", "id3151107_data");
    
    $name = $_POST["name"];
    $username = $_POST["username"];
    $age = $_POST["age"];
    $password = $_POST["password"];
  
    $checking = mysqli_query($con, "SELECT username FROM user WHERE username = '$username'");
    $count = mysqli_num_rows($checking);
    
    if ($count != 0){
        
        $response["success"] = false;
        
    } else {
        
    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, age, password, score) VALUES (?, ?, ?, ?, 0)");
    mysqli_stmt_bind_param($statement, "ssis", $name, $username, $age, $password); 
    mysqli_stmt_execute($statement);
    $response = array();
    $response["success"] = true;  
    
    }
     echo json_encode($response);
?>
