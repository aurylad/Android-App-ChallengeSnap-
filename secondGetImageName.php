<?php

$con = mysqli_connect("localhost", "id3151107_challengesnap", "dviratis00", "id3151107_data");

$username = $_POST["username"];
$idAuto = $_POST["idAuto"];

$statement = mysqli_prepare($con, "SELECT image_path, id_auto, image_name, date, challenge_title FROM images WHERE id_username = ? AND id_auto != ? AND id_auto > ? LIMIT 1");
mysqli_stmt_bind_param($statement, "sii", $username, $idAuto, $idAuto);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $image_path, $id_auto, $image_name, $date, $challenge_title);

$response = array();
$response["success"] = false;

while (mysqli_stmt_fetch($statement)){
	    $response["success"] = true;  
        $response["image_path"] = $image_path;
        $response["id_auto"] = $id_auto;
        $response["image_name"] = $image_name;
        $response["date"] = $date;
        $response["challenge_title"] = $challenge_title;
        
}
 echo json_encode($response);
 
?>