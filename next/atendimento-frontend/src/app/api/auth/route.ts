export async function POST(req: Request) {
  const body = await req.json(); // parse the JSON body
  try {
    const response = await fetch('http://localhost:8080/auth/login', { //change to cases later
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include',
      body: JSON.stringify({ username: body.username, password: body.password })
    });
    //const data = await response.json();
    console.log("asdasdfalk;sjfaslkjfasljfkasjkl;");
    console.log(response);
    const data = await response.text();
    //return null;
    if(response.ok) {
      const cookie = response.headers.get('set-cookie');
      return new Response(JSON.stringify({ msg: data}),{
          status: 200,
          headers: {
            'Content-Type': 'application/json',
            ...(cookie ? { 'Set-Cookie': cookie } : {})
          },
        });
      }
      // return new Response(JSON.stringify(data), {
      //   status: 200,
      //   headers: { 'Content-Type': 'application/json' },
      // });
    //}
    //console.log(data);
    return null;
    throw new Error(`${data.password == null ? data.error: data.password}`); //backend ta enviando mensagem de erro no node password nesta versão
  } catch (error) {
    console.error("Login error:", error);
    return new Response(JSON.stringify({
      message: "An error occurred during login.",
      error: error instanceof Error ? error.message : String(error)
    }), {
      status: 500,
      headers: { 'Content-Type': 'application/json' },
    });
  }
}