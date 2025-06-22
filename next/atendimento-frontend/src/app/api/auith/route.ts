export async function POST() {
  try {
    const response = await fetch('http://localhost:8080/auth/login', { //change to cases later
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username: 'Fetch POST Request Example', password: 'asdasd' })
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