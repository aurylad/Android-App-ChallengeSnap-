<?php
$con = mysqli_connect("localhost", "id3151107_challengesnap", "dviratis00", "id3151107_data");
    
    $score = $_POST["score"];
    $username = $_POST["username"];
    $challengeTitle = $_POST["challengeTitle"];
    
    $statement = mysqli_prepare($con, "UPDATE user SET score= ? WHERE username= ?;");
    mysqli_stmt_bind_param($statement, "is", $score, $username); 
    mysqli_stmt_execute($statement);
    
    $statement2 = mysqli_prepare($con, "UPDATE user SET challengeTitle = CONCAT(challengeTitle, ?) WHERE username= ?");
    mysqli_stmt_bind_param($statement2, "ss", $challengeTitle, $username); 
    mysqli_stmt_execute($statement2);
    
    $response = array();
    $response["success"] = true;  
    
     echo json_encode($response);
?>
