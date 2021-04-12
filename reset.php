<?php
$servername = "cslinux0.comp.hkbu.edu.hk";
$dbname = "comp4107_grp11";
$accname = "comp4107_grp11";
$password = "646586";

//create connection
$conn = new mysqli($servername, $accname, $password, $dbname);  //server, user, pw, db

if ($conn->connect_error){
  die("Connection failed: " . $conn->connect_error);
}
if (!mysqli_select_db($conn, $dbname)) {
  die("Uh oh, couldn't select database $dbname");
}

$queries = [
  "TRUNCATE TABLE Account",
  "TRUNCATE TABLE Card",
  "INSERT INTO Card VALUES('1005-5001', '456123789')",
  "INSERT INTO Card VALUES('2026-6202', '456123789')",
  "INSERT INTO Card VALUES('4107-7014', '456123789')",

  "INSERT INTO Account VALUES('4107-7014', 9999, 'CHAN TAI MAN', '111-222-331')",
  "INSERT INTO Account VALUES('2026-6202', 9999, 'CHAN SIU MAN', '111-222-332')",
  "INSERT INTO Account VALUES('1005-5001', 9999, 'CHAN MAY MAY', '111-222-333')",
  "INSERT INTO Account VALUES('1005-5001', 9999, 'CHAN MAY MAY', '111-222-334')",
  "INSERT INTO Account VALUES('1005-5001', 9999, 'CHAN MAY MAY', '111-222-335')",
  "INSERT INTO Account VALUES('1005-5001', 9999, 'CHAN MAY MAY', '111-222-336')"
];

foreach ($queries as $query) {
  $stmt = $conn->prepare($query);
  $stmt->execute();
}


$conn->close();
?>
