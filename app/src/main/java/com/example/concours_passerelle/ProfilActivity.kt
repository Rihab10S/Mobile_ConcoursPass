package com.example.concours_passerelle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle

class ProfilActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)


        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Configurer la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurer les actions du menu
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_Dashboard -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                }
                R.id.nav_profil -> {
                    startActivity(Intent(this, ProfilActivity::class.java))
                }
                R.id.nav_cursus -> {
                    // Gérer la navigation vers "Cursus"
                }
                R.id.nav_messages -> {

                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        val btnEditProfil: Button = findViewById(R.id.btnEditProfil)
        btnEditProfil.setOnClickListener {
            // Démarrer EditProfilActivity
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
