export async function GET(req: Request) {
  try {
    const cookie = req.headers.get('cookie') ?? '';
    
    const response = await fetch('http://localhost:8080/admin/users', { //change to cases later
      method: 'GET',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json',
        'Cookie': cookie, // 🔥 repassando o cookie do navegador
        // 'Authorization': ... se quiser continuar usando também
      },
    });
    const data = await response.json();
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