import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

// format: off
private val firstNames = List("Emma", "Liam", "Olivia", "Noah", "Ava", "Ethan", "Isabella", "Mason", "Sophia", "Logan", "Mia", "Lucas", "Charlotte", "Aiden", "Amelia", "Benjamin", "Harper", "James", "Evelyn", "Sebastian", "Abigail", "Alexander", "Emily", "Jacob", "Madison", "Elijah", "Scarlett", "William", "Victoria", "Matthew", "Aubrey", "Henry", "Chloe", "Jack", "Grace", "Michael", "Lily", "Owen", "Zoey", "Daniel", "Natalie", "Jackson", "Addison", "Samuel", "Layla", "David", "Ella", "Joseph", "Riley", "Carter", "Penelope", "Luke", "Lillian", "Wyatt", "Zoe")
private val lastNames  = List("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts")
private val domains    = List("example.com", "mail.com", "inbox.com", "email.com", "post.com", "worldmail.com", "myself.com", "letterbox.com", "correspondence.com", "mailservice.com")
// format: on

@main def generateData(numberOfRecords: Int = 100, batchSize: Int = 10): Unit =
  val filePath = os.pwd / "mysql-init" / "02_insert_data.sql"
  os.write.over(filePath, "", createFolders = true)

  println(s"Generating data [$filePath] for [$numberOfRecords] records in batches of [$batchSize]...")

  val totalBatches = numberOfRecords / batchSize
  (1 to totalBatches).foreach { batchNum =>
    val batch = (1 to batchSize).map(_ => generateSQLRow()).mkString("", ",\n", ";")
    os.write.append(filePath, s"INSERT INTO users (name, phone, email, date_of_birth) VALUES\n$batch\n\n")
    println(s"Generated batch [$batchNum] out of [$totalBatches] batches...")
  }

  println(s"Generated data [$filePath] for [$numberOfRecords] records in batches of [$batchSize]...")

def generateSQLRow(): String =
  val name  = generateName()
  val phone = generatePhoneNumber()
  val email = generateEmail(name)
  val dob   = generateDateOfBirth()
  s"(\"$name\",\"$phone\",\"$email\",\"$dob\")"

def generateName(): String =
  val firstName = firstNames(Random.nextInt(firstNames.size))
  val lastName  = lastNames(Random.nextInt(lastNames.size))
  s"$firstName $lastName"

def generatePhoneNumber(): String =
  val prefix = Random.nextInt(999) + 1
  val mid    = Random.nextInt(999) + 1
  val end    = Random.nextInt(9999) + 1
  f"1-$prefix%03d-$mid%03d-$end%04d"

def generateEmail(name: String): String =
  val domain    = domains(Random.nextInt(domains.size))
  val emailName = name.toLowerCase.replaceAll(" ", ".")
  s"$emailName@$domain"

def generateDateOfBirth(): String =
  val formatter   = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val randomYear  = Random.between(1950, 2020)
  val randomMonth = Random.between(1, 12)
  val randomDay   = Random.between(1, 28)
  LocalDate.of(randomYear, randomMonth, randomDay).format(formatter)
