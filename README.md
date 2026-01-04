# Spring_Security_JWT
Implemented Spring security with JWT token

# Springboot version=3.5+
# Spring Security version= 6.x+

# Stateful Vs Stateless
# JWT
->Json web token
->3 parts(Headder,Payload,Signature)
Header-algorithm name
Payload=Date
Signature=Date

# Bcrypt Algorithm encoder:- 
1)protect from brute force attack 
2)Same password & diffrent hash 
3)Same Password + Same salt=always same hash

# Login API->Authetication->Authetication Provider->DAOAuth->loadByUsername()->Password.match(new,OldHash) (Note:- Matching is done on hash)

#  @EnableWebSecurity Vs @EnableMethodSecurity
@EnableWebSecurity=apply role level acces on http method.
@EnableMethodSecurity=apply role level acces on method (@PreAuthorize("hasRole("ADMIN,USER")"))

# UserDetails Vs UserDetailsService
UserDetails=DAO 
UserDetailsService=help to fetch user data

# 
