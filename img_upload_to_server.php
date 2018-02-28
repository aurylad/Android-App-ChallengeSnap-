<?php

// Create connection
$conn = mysqli_connect("localhost", "id3151107_challengesnap", "dviratis00", "id3151107_data");
 
 if($_SERVER['REQUEST_METHOD'] == 'POST')
 {
 $DefaultId = 0;
 
 $ImageData = $_POST['image_path'];
 
 $ImageName = $_POST['image_name'];
 
 $UsernameId = $_POST['username'];
 
 $ChallengeTitle = $_POST['challengeTitle'];

 $GetOldIdSQL ="SELECT id_auto FROM images ORDER BY id_auto ASC";

 $Query = mysqli_query($conn,$GetOldIdSQL);

 
 while($row = mysqli_fetch_array($Query)){
 
 $DefaultId = $row['id_auto'];
 }
 
 $ImagePath = "images/$ImageName.png";
 
 $ServerURL = "https://challangesnap.000webhostapp.com/$ImagePath";
 
 $InsertSQL = "insert into images (id_username,image_path,image_name,challenge_title,date) values ('$UsernameId','$ServerURL','$ImageName','$ChallengeTitle', NOW())";
 
 if(mysqli_query($conn, $InsertSQL)){

 file_put_contents($ImagePath,base64_decode($ImageData));

 echo "Your Image Has Been Uploaded.";
 }
 
 mysqli_close($conn);
 }else{
 echo "Not Uploaded";
 }

?>