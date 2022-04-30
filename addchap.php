<?php
// header("Content-Type: application/json");
// header("Acess-Control-Allow-Origin: *");
// header("Acess-Control-Allow-Methods: POST,GET");
$connect=mysqli_connect('localhost','root','','doctruyen');
mysqli_set_charset($connect,'utf8');
$Matruyen=$_POST['Matruyen'];
$namechap=$_POST['namechap'];
$Content=$_POST['content'];
$array=[];
$sql="select count(*) from chap where namechap=$namechap and Matruyen=$Matruyen";
$result=mysqli_query($connect,$sql);
$number_rows=mysqli_fetch_array($result)['count(*)'];
if($number_rows>=1){
 $array['id']='';
$array['Matruyen']='';
$array['chapso']='';
$array['Content']=''; 
}
else{
    $sql="INSERT INTO chap(Matruyen, namechap, content) VALUES ($Matruyen,$namechap,'$Content')";
    $result=mysqli_query($connect,$sql);
$id=mysqli_insert_id($connect);
$array['id']=$id;
$array['Matruyen']=$Matruyen;
$array['chapso']=$namechap;
$array['Content']=$Content;   
   
}
header('Content-Type: application/json');
echo json_encode($array);