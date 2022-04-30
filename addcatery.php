<?php

$connect=mysqli_connect('localhost','root','','doctruyen');
mysqli_set_charset($connect,'utf8');
$name=$_POST['name'];
$img=$_POST['img'];
$des=$_POST['description'];
$respone="Thanh cong";
$error="Thất bại";
$sql="select count(*) from theloai where name='$name' and description='$des'";
$result=mysqli_query($connect,$sql);
$number_rows=mysqli_fetch_array($result)['count(*)'];
if($number_rows>=1){
    header('Content-Type: application/json');
    echo json_encode($error); 
   
}
else{
    $sql="INSERT INTO theloai(name, img, description) VALUES ('$name','$img','$des')";
    $result=mysqli_query($connect,$sql);
    header('Content-Type: application/json');
    echo json_encode($respone); 
   
}
