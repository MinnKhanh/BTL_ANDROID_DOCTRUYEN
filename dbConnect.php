<?php
echo"chay thanh cong";
class dbConnect {
 private $conn;
 // Phương thức khởi tạo
 function __construct() {
   // Kết nối đến database
   $this->connect();
 }
 function __destruct() {
  // Đóng kết nối
  $this->close();
 }
 /**
 * Thiết lập kết nối đến CSDL
 *
 */
 function connect() {
   include_once dirname(__FILE__) . './config.php';
  // Kết nối đến MySQL
  $conn=mysqli_connect('localhost','root','','hocphp');
  mysqli_set_charset($connect,'utf8');
  return $this->conn;
 }
 /**
 * Đóng kết nối
 */
 function close() {
   // Đóng kết nối CSDL
   mysqli_close($this->conn);
 }
}
?>