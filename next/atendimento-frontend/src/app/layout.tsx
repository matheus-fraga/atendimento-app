import "./globals.css";
import { Provider } from "@/components/ui/provider"

export const metadata = {
  title: "Atendimento App",
  description: "Sistema de Gerenciamento de Atendimentos",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {

  return (
    <html suppressHydrationWarning>
      <body>
        <Provider>{children}</Provider>
      </body>
    </html>
    
  );
}



