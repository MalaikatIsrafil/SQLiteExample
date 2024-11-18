package id.ac.polbeng.riskiafendi.sqliteexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.ac.polbeng.riskiafendi.sqliteexample.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_STUDENT = "extra_student"
    }

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var studentDBHelper: StudentDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val studentData =  intent.getParcelableExtra<StudentModel>(EXTRA_STUDENT) as StudentModel

        /*
        binding.etNama.isEnabled = false
        binding.etUmur.isEnabled = false
        binding.btnUpdate.isEnabled = false
        binding.btnHapus.isEnabled = false

         */
        binding.etNIM.isEnabled = false
        binding.etNIM.setText(studentData.nim)
        binding.etNama.setText(studentData.name)
        binding.etUmur.setText(studentData.age)


        studentDBHelper = StudentDBHelper(this)
/*
        binding.btnCari.setOnClickListener {
            val nim = binding.etNIM.text.toString()
            if(nim.isEmpty()){
                Toast.makeText(this, "Silah masukan nim terlebih dahulu!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            val students = studentDBHelper.searchStudentByNIM(nim)
            if(students.isNotEmpty()){
                binding.etNama.setText(students[0].name)
                binding.etUmur.setText(students[0].age)
                setUpdateState(true)
                Toast.makeText(this, "Mahasiswa ditemukan!",
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Mahasiswa tidak ditemukan!",
                    Toast.LENGTH_SHORT).show()
                setUpdateState(false)
            }
        }
        binding.btnCari.setOnLongClickListener {
            binding.etNIM.isEnabled = binding.etNIM.isEnabled.not()
            true
        }


 */

        binding.btnUpdate.setOnClickListener {
            val nim = binding.etNIM.text.toString()
            val name = binding.etNama.text.toString()
            val age = binding.etUmur.text.toString()
            if(nim.isEmpty() || name.isEmpty() || age.isEmpty()){
                Toast.makeText(this, "Silah masukan data nim, nama, dan umur!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
            }
            val newUpdateStudent = StudentModel(nim = nim, name =
            name, age = age)
            val updateCount =
                studentDBHelper.updateStudent(newUpdateStudent)
            if(updateCount > 0){
                Toast.makeText(this, "Mahasiswa yang terupdate : $updateCount", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Tidak ada data mahasiswa yang diupdate silahkan coba lagi!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnHapus.setOnClickListener {
            // Gunakan kelas Builder untuk konstruksi dialog yang nyaman.
            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("Konfirmasi Hapus Data")
                .setMessage("Apakah anda yakin?")
                .setPositiveButton("Ya") { _, _ ->
                    val nim = binding.etNIM.text.toString()
                    val deleteCount =
                        studentDBHelper.deleteStudent(nim)
                    if(deleteCount > 0) {
                        Toast.makeText(this, "Mahasiswa yang terhapus : $deleteCount", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Tidak ada data mahasiswa yang dihapus silahkan coba lagi!", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Tidak") { _, _ ->
                    // Pengguna membatalkan dialog.
                }
            // Buat objek AlertDialog dan kembalikan.
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
/*
    private fun setUpdateState(state: Boolean){
        binding.etNIM.isEnabled = !state
        binding.etNama.isEnabled = state
        binding.etUmur.isEnabled = state
        binding.btnUpdate.isEnabled = state
        binding.btnHapus.isEnabled = state
    }

 */
    override fun onDestroy() {
        studentDBHelper.close()
        super.onDestroy()
    }

    /*
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityUpdateBinding.inflate(layoutInflater)
            setContentView(binding.root)

            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

     */

}