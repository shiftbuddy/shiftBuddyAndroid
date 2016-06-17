<?PHP 
    include_once("connection.php"); 
    if( isset($_POST['txtUsername']) && isset($_POST['txtPassword']) ) { 
        $user = $_POST['txtUsername'];
        $pword = $_POST['txtPassword'];
        $query = "SELECT username, password FROM tbl_client ". 
        " WHERE username = '$user' AND password = '$pword'"; 
        
	$result = mysqli_query($conn, $query);
	if($result->num_rows == 1){
		while($row = mysqli_fetch_assoc($result)) {
			echo json_encode(array("userdetails"=>$row));
			//echo "username".$row["username"];
		}
		//echo json_encode(array("result"=>$result));
	} else {
		echo 'oops! Please try again!';
	}
	//$check = mysqli_fetch_array(mysqli_query($con,$sql));

	//if(isset($check)){
	//	array_push($result,array("username"=>$user,"password"=>$pword));
	//	echo json_encode(array("result"=>$result));

	//} else{
	//	echo 'oops! Please try again!';
	//}
	mysqli_close($con);
	}else{
		echo 'error';
	}


        //$result = mysqli_query($conn, $query);
        
        //if($result->num_rows > 0){
            //if(isset($_POST['mobile']) && $_POST['mobile'] == "android"){ 
          //      echo "success"; 
           //     exit; 
            //} 
        //} else{ 
          //  echo "Login Failed <br/>"; 
        //} 
    //} 
?>
