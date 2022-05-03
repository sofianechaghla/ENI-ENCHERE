<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<main class="home">
	<article class="Article-Page">
		<div class="box-title">
			<div class="img"></div>
			<div class="title">
				<div class="groupForm">
					<p>
						<strong>${article.nom_article}</strong>
					</p>
				</div>
				<div class="groupForm">
					<p>
						<strong>${article.description }</strong>
					</p>
				</div>
				<div class="groupForm">
					<c:forEach var="cat" items="${categorie}">
						<c:if test="${cat.no_categorie == article.no_categorie}">
							<p>
								<strong>Catégorie: </strong><strong>${cat.libelle}</strong>
							</p>
						</c:if>
					</c:forEach>
				</div>
				<div class="groupForm">
					<p>
						<strong>Prix de départ: </strong><strong>${article.prix_initial}
							pts</strong>
					</p>
				</div>
			</div>
		</div>
		<hr />
		<div class="box-details">
			<div class="groupForm">
				<p>
					<strong>Début de l'enchère: </strong><strong><fmt:formatDate
							pattern="dd/MM/yy" value="${article.date_debut_encheres}" /></strong>
				</p>
			</div>
			<hr>
			<div class="groupForm">
				<p>
					<strong>Fin de l'enchère: </strong> <strong><fmt:formatDate
							pattern="dd/MM/yy" value="${article.date_fin_encheres}" /></strong>
				</p>
			</div>
			<hr>
			<div class="groupForm">
				<p>
					<strong>Vendeur: </strong><a
						href="${context}/MonProfil?idVendeur=${vendeur.no_utilisateur }">${vendeur.pseudo}</a>
				</p>
			</div>
			<hr>
			<div class="groupForm">
				<p>
					<strong>Retrait: </strong><strong class="rue">${vendeur.rue }&ensp;&ensp;${vendeur.code_postal }&ensp;${vendeur.ville }</strong>
				</p>
			</div>
		</div>
	</article>
	<section class="Article-Page">
		<%@include file="../includes/erreursSession.jsp"%>
		<%@include file="../includes/messageConfirmation.jsp"%>
		<div class="bestAuction">
			<c:if test="${enchere.no_utilisateur > 0}">
				<p>Meilleure enchère actuelle</p>
				<p>
					<strong>${utilisateur.pseudo == bestEnchere.pseudo ? "Vous" : bestEnchere.pseudo}
						avec </strong> <strong>${enchere.montant_enchere} pts</strong>
				</p>
			</c:if>
			<c:if test="${enchere.no_utilisateur < 0}">
				<p>
					<strong>Il n'y a pas encore d'enchère pour cette article.</strong><strong>Soyez
						le premier a enchérir !</strong>
				</p>
			</c:if>
			<c:choose>
				<c:when
					test="${utilisateur != null && utilisateur.pseudo != vendeur.pseudo}">
					<c:if test="${etatEnchere == 'EC'}">
						<form action="Article?idArticle=${article.no_article}" method="post"
							class="formEncherir">
							<label for="prix">Votre proposition:</label> <input type="number"
								id="prix" name="howmuch">
							<button type="submit">Encherir</button>
						</form>
					</c:if>
					<c:if test="${etatEnchere == 'CR'}">
						<div class="formEncherir">
							<p>L'enchère n'a pas débutée !</p>
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'EC'}">
						<div class="formEncherir">
							<p>L'enchère est en cours ...</p>
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'TR'}">
						<div class="formEncherir">
							<c:if test="${utilisateur.pseudo == bestEnchere.pseudo}">
								<p>Bravo Vous remporté l'enchère !</p>
							</c:if>
							<c:if test="${utilisateur.pseudo != bestEnchere.pseudo}">
								<p>Dommage vous n'avez pas remporté l'enchère.</p>
							</c:if>
							
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'RE'}">
						<div class="formEncherir">
							<p>L'article a été remis !</p>
						</div>
					</c:if>


				</c:when>
				<c:when
					test="${utilisateur != null && utilisateur.pseudo == vendeur.pseudo}">

					<c:if test="${etatEnchere == 'CR'}">
						<div class="formEncherir">
							<p>L'enchère n'a pas débutée !</p>
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'EC'}">
						<div class="formEncherir">
							<p>L'enchère est en cours ...</p>
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'TR'}">
						<div class="formEncherir">
							<p>L'enchère est terminée !</p>
						</div>
					</c:if>
					<c:if test="${etatEnchere == 'RE'}">
						<div class="formEncherir">
							<p>L'article a été remis !</p>
						</div>
					</c:if>

				</c:when>
				<c:otherwise>
					<div class="formEncherir _p">
						<p class="_p1">Si vous souhaitez encherir, veuillez </p>
							<p class="_p2"> <a
								href="${context}/Connexion">vous connecter</a> ou <a
								href="${context}/Inscription">créer un compte</a>
						</p>
					</div>
				</c:otherwise>
			</c:choose>

		</div>
		<c:if test="${utilisateur.no_utilisateur == article.no_utilisateur }">
		<c:if test="${etatEnchere == 'CR'}">
				<div class="formEncherir">
					<p>
						<a href="${context}/ModifierArticle?idArticle=${article.no_article}">Modifier</a>
						<a href="${context}/SupprimerArticle?idArticle=${article.no_article}">Supprimer</a>
					</p>
				</div>
			</c:if>
		<c:if test="${etatEnchere == 'TR'}">
				<div class="formEncherir">
					<p>
						<a href="${context}/RetraitArticle?idArticle=${article.no_article}">Confirmer le retrait</a>
					</p>
				</div>
			</c:if>
			<c:if test="${etatEnchere == 'RE'}">
				<div class="formEncherir">
					<p>
						L'article a été remis au gagnant de l'enchère.
					</p>
				</div>
			</c:if>
		
		</c:if>
		

		<div class="ListEnchere">
			<h2>Historique des enchères</h2>
			<hr />
			
			<c:if test="${encheresArticle.size() == 0}"><p class="enchereNull">Il n'existe aucune enchère pour cet article.</p></c:if>
			<c:forEach var="ench" items="${encheresArticle}">
				<p>
					<strong>${ench.pseudo}</strong><strong>${ench.montant_enchere}pts</strong>
				</p>
				<hr />
			</c:forEach>
		</div>
	</section>
</main>



<!-- test="${etatEnchere == 'EC'}"-->