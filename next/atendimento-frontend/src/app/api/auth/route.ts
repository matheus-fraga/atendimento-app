export async function POST(req: Request) {
  const body = await req.json(); // parse the JSON body
  console.log(JSON.stringify({ username: body.username, password: body.password }));
  try {
    const response = await fetch('http://localhost:8080/auth/login', { //change to cases later
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username: body.username, password: body.password })
    });
    const data = await response.json();
    if(response.ok) {
      return new Response(JSON.stringify(data), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }
    console.log(data);
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