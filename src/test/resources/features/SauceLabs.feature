
@LoginTest
 Feature: Sauce Labs App

   @Login1
   Scenario: Login Ok
     Given me encuentro en la venta inicial de SauceDemo
     And me dirigo a la venta de login
     And valido que se encuentre en el login
     When inicio sesión con mi usuario "bod@example.com" y clave "10203040"
     Then valido que el inicio de session es exitoso

     @Login2
   Scenario: Login 2 Ok
     Given ingreso al aplicativo de SauceLabs
     When ingreso el usuario "standard_user"
     And ingreso la clave "secret_sauce"
     And hago clic en LOGIN
     Then valido el login OK