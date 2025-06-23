export async function PATCH(req: Request) {
  try {
    const reqBody = await req.json();
    console.log(reqBody);
    const cookie = req.headers.get('cookie') ?? '';
    
    const response = await fetch(`http://localhost:8080/supervisor/atendimentos/${reqBody.protocol}/editar`, { //change to cases later
      method: 'PATCH',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'Cookie': cookie
      },
      body: JSON.stringify({
        novaDescricao: reqBody.novaDescricao,
      }),
    });
    const data = await response.json();
    console.log(data);
    if(response.ok) {
      return new Response(JSON.stringify(data), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }
    throw new Error(`HTTP ${data.status}: ${data.statusText}`);
  } catch (error) {
    console.error('Error proxying request:', error);
    return new Response(
      JSON.stringify({ error: 'Internal server error' }),
      { status: 500 }
    );
  }
}