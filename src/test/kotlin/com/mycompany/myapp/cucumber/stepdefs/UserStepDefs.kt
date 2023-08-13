package com.mycompany.myapp.cucumber.stepdefs

import io.cucumber.java.Before
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

import com.mycompany.myapp.web.rest.UserResource
import com.mycompany.myapp.security.ADMIN

import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.MockMvc

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserStepDefs: StepDefs() {

    @Autowired
    private lateinit var userResource: UserResource

    

    private lateinit var userResourceMock: MockMvc

    @Before
    fun setup() {
        val grantedAuthorities = mutableListOf<GrantedAuthority>()
        grantedAuthorities.add(SimpleGrantedAuthority(ADMIN))
        val principal = User("username", "", true, true, true, true, grantedAuthorities)
        val authentication = UsernamePasswordAuthenticationToken(
            principal,
            principal.password,
            principal.authorities
        )
        val context = SecurityContextHolder.createEmptyContext()
        context.setAuthentication(authentication)
        SecurityContextHolder.setContext(context)
        userResourceMock = MockMvcBuilders.standaloneSetup(userResource).build()
    }

    @When("I search user {string}")
    fun i_search_user(userId: String) {
        actions = userResourceMock.perform(get("/api/admin/users/" + userId).accept(MediaType.APPLICATION_JSON))
    }

    @Then("the user is found")
    fun the_user_is_found() {
        actions?.let {
            it.andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        }
    }

    @Then("his last name is {string}")
    fun his_last_name_is(lastName: String) {
        actions?.let { it.andExpect(jsonPath("\$.lastName").value(lastName)) }
        
    }

}
